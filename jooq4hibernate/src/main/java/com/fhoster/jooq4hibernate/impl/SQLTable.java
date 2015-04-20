package com.fhoster.jooq4hibernate.impl;

class SQLTable {
    private String name;
    private String schema;
    private String alias;

    public SQLTable() {}

    public String getName()
    {
        return name;
    }

    public void setName(
        String name)
    {
        this.name = name;
    }

    public String getAlias()
    {
        return alias != null ? alias : name;
    }

    public void setAlias(
        String alias)
    {
        this.alias = alias;
    }

    public String getSchema()
    {
        return schema;
    }

    public void setSchema(
        String schema)
    {
        this.schema = schema;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alias == null) ? 0 : alias.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((schema == null) ? 0 : schema.hashCode());
        return result;
    }

    @Override
    public boolean equals(
        Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SQLTable other = (SQLTable) obj;
        if (alias == null) {
            if (other.alias != null) {
                return false;
            }
        }
        else if (!alias.equals(other.alias)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        if (schema == null) {
            if (other.schema != null) {
                return false;
            }
        }
        else if (!schema.equals(other.schema)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "SQLTable [name=" + name + ", alias=" + alias + ", schema=" + schema + "]";
    }
}