/**
 * Copyright 2011 Microsoft Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.microsoft.windowsazure.services.blob.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.microsoft.windowsazure.services.blob.implementation.ContainerACLDateAdapter;

public class ContainerACL {
    private String etag;
    private Date lastModified;
    private PublicAccessType publicAccess;
    private List<SignedIdentifier> signedIdentifiers = new ArrayList<SignedIdentifier>();

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public PublicAccessType getPublicAccess() {
        return publicAccess;
    }

    public void setPublicAccess(PublicAccessType publicAccess) {
        this.publicAccess = publicAccess;
    }

    public List<SignedIdentifier> getSignedIdentifiers() {
        return signedIdentifiers;
    }

    public void setSignedIdentifiers(List<SignedIdentifier> signedIdentifiers) {
        this.signedIdentifiers = signedIdentifiers;
    }

    public void addSignedIdentifier(String id, Date start, Date expiry, String permission) {
        AccessPolicy accessPolicy = new AccessPolicy();
        accessPolicy.setStart(start);
        accessPolicy.setExpiry(expiry);
        accessPolicy.setPermission(permission);

        SignedIdentifier signedIdentifier = new SignedIdentifier();
        signedIdentifier.setId(id);
        signedIdentifier.setAccessPolicy(accessPolicy);

        this.getSignedIdentifiers().add(signedIdentifier);
    }

    @XmlRootElement(name = "SignedIdentifiers")
    public static class SignedIdentifiers {
        private List<SignedIdentifier> signedIdentifiers = new ArrayList<SignedIdentifier>();

        @XmlElement(name = "SignedIdentifier")
        public List<SignedIdentifier> getSignedIdentifiers() {
            return signedIdentifiers;
        }

        public void setSignedIdentifiers(List<SignedIdentifier> signedIdentifiers) {
            this.signedIdentifiers = signedIdentifiers;
        }
    }

    public static class SignedIdentifier {
        private String id;
        private AccessPolicy accessPolicy;

        @XmlElement(name = "Id")
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @XmlElement(name = "AccessPolicy")
        public AccessPolicy getAccessPolicy() {
            return accessPolicy;
        }

        public void setAccessPolicy(AccessPolicy accessPolicy) {
            this.accessPolicy = accessPolicy;
        }
    }

    public static class AccessPolicy {
        private Date start;
        private Date expiry;
        private String permission;

        @XmlElement(name = "Start")
        @XmlJavaTypeAdapter(ContainerACLDateAdapter.class)
        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        @XmlElement(name = "Expiry")
        @XmlJavaTypeAdapter(ContainerACLDateAdapter.class)
        public Date getExpiry() {
            return expiry;
        }

        public void setExpiry(Date expiry) {
            this.expiry = expiry;
        }

        @XmlElement(name = "Permission")
        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }
    }

    public static enum PublicAccessType {
        NONE, BLOBS_ONLY, CONTAINER_AND_BLOBS,
    }
}
