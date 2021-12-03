package edu.illinois.library.cantaloupe.processor.codec;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import edu.illinois.library.cantaloupe.config.Configuration;
import edu.illinois.library.cantaloupe.image.Compression;
import edu.illinois.library.cantaloupe.image.Dimension;
import edu.illinois.library.cantaloupe.image.Format;
import edu.illinois.library.cantaloupe.image.Metadata;
import edu.illinois.library.cantaloupe.image.Rectangle;
import edu.illinois.library.cantaloupe.image.ScaleConstraint;
import edu.illinois.library.cantaloupe.operation.Crop;
import edu.illinois.library.cantaloupe.operation.CropByPercent;
import edu.illinois.library.cantaloupe.operation.ReductionFactor;
import edu.illinois.library.cantaloupe.operation.Scale;
import edu.illinois.library.cantaloupe.source.StreamFactory;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageReadParam;
import javax.imageio.stream.ImageInputStream;
import org.lsst.fits.imageio.CameraImageReader;
import org.lsst.fits.imageio.CameraImageReadParam;

public final class RAFTImageReader extends AbstractIIOImageReader
        implements ImageReader {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(RAFTImageReader.class);

    static final String IMAGEIO_PLUGIN_CONFIG_KEY
            = "processor.imageio.raft.reader";
    
    private Map<String, Object> options = new HashMap();

    private final LoadingCache<File, long[]> scaleCache = Caffeine.newBuilder()
            .build((File file) -> {
                long[] counts = new long[1 << 18];
                try ( DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                    for (int i = 0; i < counts.length; i++) {
                        counts[i] = in.readLong();
                    }
                    return counts;
                } catch (IOException x) {
                    LOGGER.debug("Could not read {}", file, x);
                    return null;
                }
            });

    @Override
    protected String[] getApplicationPreferredIIOImplementations() {
        return new String[]{"org.lsst.fits.imageio.CameraImageReader"};
    }

    @Override
    public Compression getCompression(int imageIndex) {
        return Compression.UNCOMPRESSED;
    }

    @Override
    protected Format getFormat() {
        return Format.get("raft");
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public Metadata getMetadata(int imageIndex) throws IOException {
        return null;
    }

    @Override
    protected String getUserPreferredIIOImplementation() {
        Configuration config = Configuration.getInstance();
        return config.getString(IMAGEIO_PLUGIN_CONFIG_KEY);
    }

    @Override
    public boolean canSeek() {
        return true;
    }

    @Override
    public int getNumResolutions() throws IOException {
        return 10; // FIXME
    }

    /**
     * <p>Attempts to read an image as efficiently as possible, exploiting its
     * tile layout, if possible.</p>
     *
     * <p>This implementation is optimized for mono-resolution images.</p>
     *
     * <p>After reading, clients should check the reader hints to see whether
     * the returned image will require cropping.</p>
     *
     * @param imageIndex      Image index.
     * @param crop            May be {@code null}.
     * @param scale           May be {@code null}.
     * @param scaleConstraint Scale constraint.
     * @param reductionFactor The {@link ReductionFactor#factor} property will
     *                        be modified to reflect the reduction factor of the
     *                        returned image.
     * @param hints           Will be populated by information returned from
     *                        the reader.
     * @return                Image best matching the given arguments.
     */
    
    @Override
    public BufferedImage read(final int imageIndex,
                              final Crop crop,
                              final Scale scale,
                              final ScaleConstraint scaleConstraint,
                              final ReductionFactor reductionFactor,
                              final Set<ReaderHint> hints) throws IOException {

        LOGGER.info("RAFT: ReductionFactor=" + reductionFactor);
        LOGGER.info("RAFT: hints=" + hints);
        LOGGER.info("RAFT: iioReader=" + iioReader);

        // TODO: Review the logic here, the primary goal is just to deal with fetching the full focal-plane
        // image preview, without exceeding the maximum size of a java 2d image.
        if (scale != null && iioReader != null) {
            Crop actualCrop = crop == null ? new CropByPercent() : crop;
            final Dimension fullSize = new Dimension(iioReader.getWidth(0), iioReader.getHeight(0));
            final Rectangle regionRect = actualCrop.getRectangle(fullSize, new ReductionFactor(), scaleConstraint);
            LOGGER.info("RAFT: regionRect=" + regionRect);
            Dimension resultingSize = scale.getResultingSize(fullSize, reductionFactor, scaleConstraint);
            LOGGER.info("RAFT: resultingSize=" + resultingSize);
            // TODO: Do we need to be able to get option specified in the URL (rather than in the input file?)
            ImageReadParam readParam = getDefaultReadParam(Collections.EMPTY_MAP);
            int subSamplingX = 1;
            int subSamplingY = 1;
            if (regionRect.width() * regionRect.height() > Integer.MAX_VALUE) {
//                int subSamplingX = (int) Math.floor(regionRect.width()/resultingSize.width());
//                int subSamplingY = (int) Math.floor(regionRect.height()/resultingSize.height());
                subSamplingX = 16;
                subSamplingY = 16;
            }
            readParam.setSourceRegion(regionRect.toAWTRectangle());
            readParam.setSourceSubsampling(subSamplingX, subSamplingY, 0, 0);
            LOGGER.info("RAFT: subsampling=" + subSamplingX + " " + subSamplingY);
            LOGGER.info("RAFT: sourceRegion=" + readParam.getSourceRegion());
            hints.add(ReaderHint.ALREADY_CROPPED);
            return iioReader.read(0, readParam);
        } else {
            return super.read(imageIndex, crop, scale, scaleConstraint, reductionFactor, hints);
        }
    }

    @Override
    public BufferedImage read(int imageIndex) throws IOException {
        ImageReadParam readParam = getDefaultReadParam(Collections.EMPTY_MAP);
        return iioReader.read(imageIndex, readParam);
    }

    public ImageReadParam getDefaultReadParam(Map<String, Object> opts) {
        CameraImageReadParam readParam = ((CameraImageReader) iioReader).getDefaultReadParam();
        this.options.putAll(opts);
        Object biasCorrection = this.options.get("biasCorrection");
        if (biasCorrection != null) {
            if ("None".equals(biasCorrection)) biasCorrection = "Simple Overscan Subtraction only";
            else if ("Simple Overscan Correction".equals(biasCorrection)) biasCorrection = "Simple Overscan Subtraction2";
            readParam.setBiasCorrection(biasCorrection.toString());
        }
        Object scale = this.options.get("scale");
        if (scale != null) {
            readParam.setScale("Global".equalsIgnoreCase(scale.toString()) ? CameraImageReadParam.Scale.GLOBAL : CameraImageReadParam.Scale.AMPLIFIER);
        }
        Object colorMap = this.options.get("colorMap");
        if (colorMap != null) {
            readParam.setColorMap(colorMap.toString());
        }
        Object globalScale = this.options.get("globalScale");
        if (globalScale != null) {
            long[] counts = scaleCache.get(new File(globalScale.toString()));
            if (counts != null) {
                readParam.setGlobalScale(counts);
            }
        }
        return readParam;
    }

    @Override
    public void setSource(ImageInputStream inputStream) throws IOException {
        super.setSource(inputStream);
        LOGGER.info("RAFT: setSource");
        extractOptionsFromFile(inputStream);
    }

    private void extractOptionsFromFile(ImageInputStream inputStream1) throws IOException {
        options.clear();
        Pattern pattern = Pattern.compile("#(\\w+)=(.*)");
        inputStream1.seek(0);
        for (;;) {
            String line = inputStream1.readLine();
            LOGGER.info("RAFT: line={}", line);
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) {
                break;
            } else {
                String key = matcher.group(1);
                String token = matcher.group(2);
                options.put(key,token);
                LOGGER.info("RAFT: option {}={}", key, token);
            }
        }
        inputStream1.seek(0);
    }

    @Override
    public void setSource(StreamFactory streamFactory) throws IOException {
        super.setSource(streamFactory); 
        LOGGER.info("RAFT: setSourceFactory");
        extractOptionsFromFile(inputStream);
    }

    @Override
    public void setSource(Path inputFile) throws IOException {
        super.setSource(inputFile); //To change body of generated methods, choose Tools | Templates.
        LOGGER.info("RAFT: setSourcePath");
        extractOptionsFromFile(inputStream);
    }

    public CameraImageReader getIioReader() {
        return (CameraImageReader) iioReader;
    }

}
