package edu.illinois.library.cantaloupe.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import edu.illinois.library.cantaloupe.Application;
import edu.illinois.library.cantaloupe.cache.DerivativeCache;
import edu.illinois.library.cantaloupe.processor.Processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.lsst.fits.imageio.CameraImageReader;

/**
 * <p>
 * Contains JSON-serializable information about an image, including its format,
 * dimensions, embedded metadata, subimages, and tile sizes&mdash; essentially a
 * superset of characteristics of all {@link Format formats} supported by the
 * application.</p>
 *
 * <p>
 * Instances are format-, {@link Processor}-, and endpoint-agnostic. An instance
 * describing a particular image {@link Processor#readInfo() returned
 * from one processor} should be {@link #equals equal} to an instance describing
 * the same image returned from a different processor. This preserves the
 * freedom to change processor assignments without invalidating any
 * {@link DerivativeCache#getInfo(Identifier) cached instances}.</p>
 *
 * <p>
 * All sizes are raw pixel data sizes, disregarding orientation.</p>
 *
 * <p>
 * Instances ultimately originate from {@link Processor#readInfo()}, but
 * subsequently they can be {@link DerivativeCache#put(Identifier, Info)
 * cached}, perhaps for a very long time. When an instance is needed, it may be
 * preferentially acquired from a cache, with a processor being consulted only
 * as a last resort. As a result, changes to the class definition need to be
 * implemented carefully so that {@link InfoDeserializer older serializations
 * remain readable}. (Otherwise, users might have to purge their cache whenever
 * the class design changes.)</p>
 *
 * <h1>History</h1>
 *
 * <p>
 * See {@link Serialization}.</p>
 */
//@JsonSerialize(using = InfoSerializer.class)
//@JsonDeserialize(using = InfoDeserializer.class)
public class CCSInfo {

    private final List<String> arg;
    private final String processorName;

    public CCSInfo(List<String> args, String processorName) {
        this.arg = args;
        this.processorName = processorName;
    }

    public List<String> getArg() {
        return arg;
    }

    public String getProcessorName() {
        return processorName;
    }

    private static ObjectMapper newMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // This module obscures Optionals from the serialization (e.g.
        // Optional.empty() maps to null rather than { isPresent: false })
        mapper.registerModule(new Jdk8Module());
        return mapper;
    }

    /**
     * @return JSON representation of the instance.
     */
    public String toJSON() throws JsonProcessingException {
        return newMapper().writer().writeValueAsString(this);
    }

    @Override
    public String toString() {
        try {
            return toJSON();
        } catch (JsonProcessingException e) { // this should never happen
            return super.toString();
        }
    }

    /**
     * @param os Output stream to write to.
     */
    public void writeAsJSON(OutputStream os) throws IOException {
        newMapper().writer().writeValue(os, this);
    }

    public static class CCSGeometryInfo extends CCSInfo {

        private final List<CameraImageReader.SegmentGeometry> geometry;

        public CCSGeometryInfo(List<String> args, String processorName, List<CameraImageReader.SegmentGeometry> geometry) {
            super(args, processorName);
            this.geometry = geometry;
        }

        public List<CameraImageReader.SegmentGeometry> getGeometry() {
            return geometry;
        }
    }

    public static class CCSPixelInfo extends CCSInfo {

        private final int x;
        private final int y;
        private final Number pixel;

        public CCSPixelInfo(List<String> args, String processorName, int x, int y, Number pixel) {
            super(args, processorName);
            this.x = x;
            this.y = y;
            this.pixel = pixel;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Number getPixel() {
            return pixel;
        }
    }

}
