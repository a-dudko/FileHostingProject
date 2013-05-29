package by.bsu.fpmi.second.dudkoAA.model;

import javax.persistence.*;
import java.lang.Integer;
import java.lang.String;

@Entity
@Table(name = "FILE")
public class File {
    @Id
    @GeneratedValue
    @Column(name = "FILE_ID")
    private Integer id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "REMOVE_CODE")
    private String removeCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRemoveCode() {
        return removeCode;
    }

    public void setRemoveCode(String removeCode) {
        this.removeCode = removeCode;
    }
}
