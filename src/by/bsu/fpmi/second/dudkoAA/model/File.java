package by.bsu.fpmi.second.dudkoAA.model;

import javax.persistence.*;

/** A class for File objects, uploaded to the site. */
@Entity
@Table(name = "FILE")
public class File {

    /** Unique file ID for DB. */
    @Id
    @GeneratedValue
    @Column(name = "FILE_ID")
    private Integer id;

    /** The name of file. */
    @Column(name = "FILE_NAME")
    private String fileName;

    /** The file description. */
    @Column(name = "DESCRIPTION")
    private String description;

    /** The author of file. */
    @Column(name = "AUTHOR")
    private String author;

    /** The author of file. */
    @Column(name = "NOTES")
    private String notes;

    /** A code to verify the file removing. */
    @Column(name = "REMOVE_CODE")
    private String removeCode;

    /**
     * Gets id of current file.
     * @return i of current file
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id for current File.
     * @param id to be set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of current file.
     * @return name of current file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets file name for current File.
     * @param fileName to be set
     */
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets description of current file.
     * @return description of current file
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description for current File.
     * @param description to be set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets author of current file.
     * @return author of current file
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author for current File.
     * @param author to be set
     */
    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * Gets notes of current file.
     * @return notes of current file
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets notes for current File.
     * @param notes to be set
     */
    public void setNotes(final String notes) {
        this.notes = notes;
    }

    /**
     * Gets remove code of current file.
     * @return removeCode of current file
     */
    public String getRemoveCode() {
        return removeCode;
    }

    /**
     * Sets removeCode for current File.
     * @param removeCode to be set
     */
    public void setRemoveCode(final String removeCode) {
        this.removeCode = removeCode;
    }
}
