package com.dpd.type;

import org.neo4j.graphdb.RelationshipType;

public enum RelType implements RelationshipType {
    EXTENDS,
    IMPLEMENT,
    ASSOCIATION,
    DEPENDENCY,
    OVERRIDE,
    INVOKING,
    RETURN_OBJECT,
    CREATE_OBJECT,
    PRIVATE_CONSTRUCT
    }
