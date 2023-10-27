package com.usda.fmsc.geospatial.base;

public interface IIDParser<ID extends Enum<ID>> {
    public ID parse(int value);
    public ID parse(String value);
}
