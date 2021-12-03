package edu.illinois.library.cantaloupe.resource.iiif.v2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.illinois.library.cantaloupe.http.Method;
import edu.illinois.library.cantaloupe.http.Status;
import edu.illinois.library.cantaloupe.image.Format;
import edu.illinois.library.cantaloupe.image.CCSInfo;
import edu.illinois.library.cantaloupe.processor.codec.ImageWriterFactory;
import edu.illinois.library.cantaloupe.resource.JacksonRepresentation;
import edu.illinois.library.cantaloupe.resource.ResourceException;
import edu.illinois.library.cantaloupe.resource.Route;
import edu.illinois.library.cantaloupe.resource.CCSRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles extended IIIF Image API 2.x information requests.
 *
 * Routes supported
 * <pre>
 * /iiif/2/<geomid>>/geometry.json (note not image specific)
 * /iiif/2/<image>/geominfo.json -> just returns geomid
 * /iiif/2/<image>/x,y/pixel.json   --> return pixel specific info
 * </pre>
 */
public class CCSResource extends IIIF2Resource {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CCSResource.class);

    private static final Method[] SUPPORTED_METHODS =
            new Method[] { Method.GET, Method.OPTIONS };

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public Method[] getSupportedMethods() {
        return SUPPORTED_METHODS;
    }

    /**
     * 
     * 
     * Writes a JSON-serialized {@link ImageInfo} instance to the response.
     */
    @Override
    public void doGET() throws Exception {
        
        String uriPath = this.getRequest().getReference().getPath();
        final List<String> args = getPathArguments();
        LOGGER.info("Got context "+uriPath+" args "+args);
        
        // TODO: we are supposed to get the available output formats from the
        // processor, but the control flow may not lead to a processor ever
        // being obtained.
        final Set<Format> availableOutputFormats =
                new HashSet<>(ImageWriterFactory.supportedFormats());

        class CustomCallback implements CCSRequestHandler.Callback {
            @Override
            public boolean authorize() throws Exception {
                try {
                    // The logic here is somewhat convoluted. See the method
                    // documentation for more information.
                    return CCSResource.this.preAuthorize();
                } catch (ResourceException e) {
                    if (Status.FORBIDDEN.equals(e.getStatus())) {
                        throw e;
                    }
                }
                return false;
            }
            @Override
            public void knowAvailableOutputFormats(Set<Format> formats) {
                availableOutputFormats.addAll(formats);
            }
        }

        try (CCSRequestHandler handler = CCSRequestHandler.builder()
                .withIdentifier(getMetaIdentifier().getIdentifier())
                .withBypassingCache(true)
                .withBypassingCacheRead(true)
                .withDelegateProxy(getDelegateProxy())
                .withRequestContext(getRequestContext())
                .withCallback(new CustomCallback())
                .withPath(uriPath)
                .withArgs(args)
                .build()) {
            CCSInfo info = handler.handle();
            addHeaders();
            new JacksonRepresentation(info).write(getResponse().getOutputStream());
        }
    }

    private void addHeaders() {
        getResponse().setHeader("Content-Type", getNegotiatedMediaType());
    }

    /**
     * @return Image URI corresponding to the given identifier, respecting the
     *         {@code X-Forwarded-*} and {@link #PUBLIC_IDENTIFIER_HEADER}
     *         reverse proxy headers.
     */
    private String getImageURI() {
        return getPublicRootReference() + Route.IIIF_2_PATH + "/" +
                getPublicIdentifier();
    }

    private String getNegotiatedMediaType() {
        String mediaType;
        // If the client has requested JSON-LD, set the content type to
        // that; otherwise set it to JSON.
        final List<String> preferences = getPreferredMediaTypes();
        if (!preferences.isEmpty() && preferences.get(0)
                .startsWith("application/ld+json")) {
            mediaType = "application/ld+json";
        } else {
            mediaType = "application/json";
        }
        return mediaType + ";charset=UTF-8";
    }

}
