package com.fhoster.jooq4hibernate.impl;

class SQLJoinCondition {

    private SQLTable leftTable, rightTable;
    private String   leftColumn, rightColumn;

    public SQLJoinCondition(SQLTable leftTable, SQLTable rightTable, String leftColumn, String rightColumn) {
        this.leftTable = leftTable;
        this.rightTable = rightTable;
        this.leftColumn = leftColumn;
        this.rightColumn = rightColumn;
    }

    public String getLeftColumn()
    {
        return leftColumn;
    }

    public String getRightColumn()
    {
        return rightColumn;
    }

    public SQLTable getLeftTable()
    {
        return leftTable;
    }

    public SQLTable getRightTable()
    {
        return rightTable;
    }

    @Override
    public int hashCode()
    {
        int hashCode = 0;
        if (leftTable != null) {
            hashCode += leftTable.hashCode();
        }
        if (rightTable != null) {
            hashCode += rightTable.hashCode();
        }
        if (leftColumn != null) {
            hashCode += leftColumn.hashCode();
        }
        if (rightColumn != null) {
            hashCode += rightColumn.hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(
        Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SQLJoinCondition)) {
            return false;
        }

        SQLJoinCondition join = (SQLJoinCondition) obj;

        if (leftColumn != null && !leftColumn.equals(join.leftColumn)) {
            return false;
        }
        if (rightColumn != null && !rightColumn.equals(join.rightColumn)) {
            return false;
        }
        if (leftTable != null && !leftTable.equals(join.leftTable)) {
            return false;
        }
        if (rightTable != null && !rightTable.equals(join.rightTable)) {
            return false;
        }
        if (join.rightTable != null && !join.rightTable.equals(rightTable)) {
            return false;
        }
        if (join.leftTable != null && !join.leftTable.equals(leftTable)) {
            return false;
        }
        if (join.rightColumn != null && !join.rightColumn.equals(rightColumn)) {
            return false;
        }
        if (join.leftColumn != null && !join.leftColumn.equals(leftColumn)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return "SQLJoinCondition [leftTable=" + leftTable + ", leftColumn=" + leftColumn + ", rightTable=" + rightTable + ", rightColumn "+rightColumn+" ]";
    }
}