package com.dreamfly.timeschedule.bo;

import java.io.Serializable;

/**
 * Created by jayden on 12/31/15.
 */
public abstract class Entity implements Serializable,Comparable {

    @Override
    public int compareTo(Object obj) {
        if(this == obj) {
            return 0;
        }
        return -1;
    }
}