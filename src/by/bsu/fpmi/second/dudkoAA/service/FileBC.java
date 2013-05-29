package by.bsu.fpmi.second.dudkoAA.service;

import by.bsu.fpmi.second.dudkoAA.dao.FileDAO;
import by.bsu.fpmi.second.dudkoAA.model.File;

import java.util.List;

public class FileBC {

    private FileDAO fileDAO = new FileDAO();

    public void addFile(File file) {
        fileDAO.add(file);
    }

    public List<File> getProfiles() {
        return fileDAO.readAll();
    }

    public int getCounts() {
        List<File> profiles = getProfiles();
        if (profiles != null) {
            return profiles.size();
        }
        return 0;
    }

    public File getFile(Integer id) {
        return fileDAO.read(id);
    }

    public void updateFile(File file) {
        fileDAO.update(file);
    }

    public void removeFile(File file) {
        fileDAO.remove(file);
    }

}
