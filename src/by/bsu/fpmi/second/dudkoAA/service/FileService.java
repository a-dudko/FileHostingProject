package by.bsu.fpmi.second.dudkoAA.service;

import by.bsu.fpmi.second.dudkoAA.dao.FileDAO;
import by.bsu.fpmi.second.dudkoAA.model.File;

import java.util.List;

/**
 * A class for interacting with files storage,
 * without dependency on what it exactly is.
 */
public class FileService {

    /** Files storage class instance. */
    private FileDAO fileDAO = new FileDAO();

    /**
     * Adds the file to the file storage.
     * @param file to add to the storage
     */
    public void addFile(final File file) {
        fileDAO.add(file);
    }

    /**
     * Gets the list of all files in the storage.
     * @return list of all files in the storage
     */
    public List<File> getFiles() {
        return fileDAO.readAll();
    }

    /**
     * Gets the counts of files in storage.
     * @return counts of files in storage
     */
    public int getCounts() {
        List<File> profiles = getFiles();
        if (profiles != null) {
            return profiles.size();
        }
        return 0;
    }

    /**
     * Gets file from the file storage by its ID.
     * @param id of file to get
     * @return file with such ID or null
     * if there is no such file
     */
    public File getFile(final Integer id) {
        return fileDAO.read(id);
    }

    /**
     * Updates file in file storage.
     * @param file to update
     */
    public void updateFile(final File file) {
        fileDAO.update(file);
    }

    /**
     * Removes file from file storage.
     * @param file to remove
     */
    public void removeFile(final File file) {
        fileDAO.remove(file);
    }
}
