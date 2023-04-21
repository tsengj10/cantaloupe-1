/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.illinois.library.cantaloupe.delegate;

import edu.illinois.library.cantaloupe.config.Configuration;
import edu.illinois.library.cantaloupe.config.Key;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;

/**
 *
 * @author tseng
 */
public class VisDelegate extends AbstractJavaDelegate implements JavaDelegate {
    
    private enum VisKey {
        PREFERRED_FORMAT("jpg");
        
        public final String key;
        
        VisKey(String key) {
            this.key = key;
        }
        
        public String key() {
            return key;
        }
        
        public String toString() {
            return key();
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
        var config = Configuration.getInstance();
        Logger.info("Hello world! The identifier is: " + getContext().getIdentifier());
        Logger.info("Input port is " + config.getInt(Key.HTTP_PORT, 80));
        Logger.info("Test key is " + config.getInt("vis.IntKey", 999));
        return true;
    }

    @Override
    public Object authorize() {
        return true;
    }

    @Override
    public Map<String,Object> getExtraIIIF2InformationResponseKeys() {
        var config = Configuration.getInstance();
        String fmt = config.getString(VisKey.PREFERRED_FORMAT, "jpg");
        return Map.ofEntries(
            new AbstractMap.SimpleEntry<>("preferredFormats", List.of(fmt))
        );
    }

    @Override
    public Map<String,Object> getExtraIIIF3InformationResponseKeys() {
        var config = Configuration.getInstance();
        String fmt = config.getString(VisKey.PREFERRED_FORMAT, "jpg");
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
