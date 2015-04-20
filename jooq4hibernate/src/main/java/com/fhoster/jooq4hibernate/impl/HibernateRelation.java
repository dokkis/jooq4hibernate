package com.fhoster.jooq4hibernate.impl;

class HibernateRelation {

    private String   name;
    private String   path;
    private Class<?> cls;

    public HibernateRelation(String name, String path, Class<?> cls) {
        super();
        this.name = name;
        this.path = path;
        this.cls = cls;
    }

    public String getName()
    {
        return name;
    }

    public void setName(
        String name)
    {
        this.name = name;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(
        String path)
    {
        this.path = path;
    }

    public Class<?> getCls()
    {
        return cls;
    }

    public void setCls(
        Class<?> cls)
    {
        this.cls = cls;
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
        HibernateRelation other = (HibernateRelation) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        }
        else if (!path.equals(other.path)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        return "JPARelation [name=" + name + ", path=" + path + ", cls=" + cls + "]";
    }
}
