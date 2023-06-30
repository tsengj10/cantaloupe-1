/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.illinois.library.cantaloupe.delegate;

import edu.illinois.library.cantaloupe.config.Configuration;
import edu.illinois.library.cantaloupe.config.Key;

import java.io.File;
import java.io.FilenameFilter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import java.util.HashMap;

/**
 *
 * @author tseng
 */
public class VisDelegate extends AbstractJavaDelegate implements JavaDelegate {
    
    private enum VisKey {
        PREFIX("vis."),
        PREFERRED_FORMAT("vis.preferredFormat"),
        CACHE_ROOT("vis.cache.root"),
        SOURCE_DEFAULT("vis.source.default"),
        SUFFIX_TYPE(".type"),
        SUFFIX_ROOT(".root"),
        SUFFIX_USE_DAYOBS(".useDayObs"),
        SUFFIX_USE_IMAGEID(".useImageId");
        
        public final String key;
        
        VisKey(String key) {
            this.key = key;
        }
        
        public String key() {
            return key;
        }   
    }
    
    private class AllFilter implements FilenameFilter {
        public AllFilter() {}
        @Override
        public boolean accept(File dir, String name) {
            return (name.endsWith(".fits"));
        }
        @Override
        public String toString() {
            return "(.*).fits";
        }
    }
    
    private class SelectFilter implements FilenameFilter {
        final private String selector;
        public SelectFilter(String sel) {
            selector = "(.*)" + sel + "(.*)";
        }
        @Override
        public boolean accept(File dir, String name) {
            return name.matches(selector);
        }
        @Override
        public String toString() {
            return selector;
        }
    }
    
    @Override
    public String serializeMetaIdentifier(Map<String,Object> metaIdentifier) {
        return null;
    }

    @Override
    public Map<String,Object> deserializeMetaIdentifier(String metaIdentifier) {
        return null;
    }

    @Override
    public Object preAuthorize() {
        return true;
    }

    @Override
    public Object authorize() {
        return true;
    }

    @Override
    public Map<String,Object> getExtraIIIF2InformationResponseKeys() {
        var config = Configuration.getInstance();
        String fmt = config.getString(VisKey.PREFERRED_FORMAT.key(), "png");
        return Map.ofEntries(
            new AbstractMap.SimpleEntry<>("preferredFormats", List.of(fmt))
        );
    }

    @Override
    public Map<String,Object> getExtraIIIF3InformationResponseKeys() {
        var config = Configuration.getInstance();
        String fmt = config.getString(VisKey.PREFERRED_FORMAT.key(), "png");
        return Map.ofEntries(
            new AbstractMap.SimpleEntry<>("preferredFormats", List.of(fmt))
        );
    }

    @Override
    public String getSource() {
        return null;
    }

    @Override
    public String getAzureStorageSourceBlobKey() {
        return null;
    }

    @Override
    public String getFilesystemSourcePathname() {
        var config = Configuration.getInstance();
        
        // Parse URL to get image identifier and selector
        String id = getContext().getIdentifier();
        String[] options = id.split("\\$");
        HashMap<String, String> map = new HashMap<>(options.length - 1);
        for (int i = 1; i < options.length; ++i) {
            String[] tokens = options[i].split("=");
            map.put(tokens[0], tokens[1]);
        }
        // make base selector raft_ccd from options
        StringBuilder sbSelector = new StringBuilder("");
        if (map.containsKey("raft")) sbSelector.append(map.get("raft"));
        if (map.containsKey("ccd")) {
            if (sbSelector.length() > 0) sbSelector.append("_");
            sbSelector.append(map.get("ccd"));
        }
        // split id into imageId and extra selector (appended to base selector)
        int idsplit = options[0].indexOf("_");
        for (int i = 1; i < 4 && idsplit > 0; ++i) idsplit = options[0].indexOf("_", idsplit + 1);
        String imageId = (idsplit < 0) ? options[0] : options[0].substring(0, idsplit);
        if (idsplit >= 0 && idsplit + 1 < options[0].length()) {
            sbSelector.append(options[0].substring(idsplit + 1));
        }
        String selector = sbSelector.toString();
        boolean selectAll = selector.isEmpty();

        // cache file = root + imageId + full selector
        StringBuilder cacheBase = new StringBuilder(config.getString(VisKey.CACHE_ROOT.key(), ""));
        cacheBase.append(imageId);
        if (!selector.isEmpty()) cacheBase.append("_").append(selector);
        
        // if (tokens.length <= 4) cachePath.append("_all"); // redundant?
        // look for cached file list
        String base = cacheBase.toString();
        if ((new File(base + ".raft")).isFile()) return base + ".raft";
        if ((new File(base + ".fp")).isFile()) return base + ".fp";
        if ((new File(base + ".cantaloupe")).isFile()) return base + ".cantaloupe";

        cacheBase.append(selectAll ? ".fp" : ".raft");
        String cacheFilename = cacheBase.toString();
        Logger.info("New cache file " + cacheFilename);
        
        // check the source type:  filesystem or database
        String source = map.getOrDefault("source", config.getString(VisKey.SOURCE_DEFAULT.key(), "filesystem"));
        String sourceBase = VisKey.PREFIX.key() + source;
        String sourceType = config.getString(sourceBase + VisKey.SUFFIX_TYPE.key(), "filesystem");
        StringBuilder path = new StringBuilder(config.getString(sourceBase + VisKey.SUFFIX_ROOT.key(), ""));
        
        // construct new file list        
        if (sourceType.equals("filesystem")) {
            if (config.getBoolean(sourceBase + VisKey.SUFFIX_USE_DAYOBS.key(), true)) {
                String[] tokens = options[0].split("_");
                path.append(tokens[2]).append("/");
            }
            if (config.getBoolean(sourceBase + VisKey.SUFFIX_USE_IMAGEID.key(), true)) {
                path.append(imageId).append("/");
            }
            // check the directory exists
            String dataPath = path.toString();
            File d = new File(dataPath);
            if (!d.isDirectory()) {
                Logger.error("Directory " + path.toString() + " does not exist");
                return null;
            }

            // new file list            
            FilenameFilter filter = selectAll ? new AllFilter() : new SelectFilter(selector);
            String[] list = d.list(filter);
            if (list.length == 0) {
                Logger.error("No files satisfy pattern " + filter.toString());
                return null;
            }
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(cacheFilename));
                for (String opt : options) bw.write("#" + opt + "\n");
                for (String s : list) bw.write(dataPath + s + "\n");
                bw.close();
            } catch (IOException io) {
                Logger.error("Unable to write file list: " + io.toString());                
            }
            
            // return cached file list
            return cacheFilename;
            
        } else if (sourceType.equals("database")) {
            Logger.error("use database source - not yet implemented");
            return null;
        }

        return null;
    }

    @Override
    public Map<String,Object> getHTTPSourceResourceInfo() {
        return null;
    }

    @Override
    public String getJDBCSourceDatabaseIdentifier() {
        return null;
    }

    @Override
    public String getJDBCSourceMediaType() {
        return null;
    }

    @Override
    public String getJDBCSourceLookupSQL() {
        return null;
    }

    @Override
    public Map<String,String> getS3SourceObjectInfo() {
        return null;
    }

    @Override
    public Map<String,Object> getOverlay() {
        return null;
    }

    @Override
    public List<Map<String,Long>> getRedactions() {
        return Collections.emptyList();
    }

    @Override
    public String getMetadata() {
        return null;
    }

}
