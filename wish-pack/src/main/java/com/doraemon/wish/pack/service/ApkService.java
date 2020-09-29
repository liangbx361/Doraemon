package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.Apk;
import com.doraemon.wish.pack.dao.repository.ApkRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.util.Optional;

@Service
public class ApkService {

    private final ApkRepository repository;

    @Value("${pack.apk-path}")
    private String apkPath;

    public ApkService(ApkRepository repository) {
        this.repository = repository;
    }

    public Apk create(Apk apk) {
        return repository.save(apk);
    }

    public Apk query(Long id) {
        Optional<Apk> apk = repository.findById(id);
        if (apk.isPresent()) {
            return apk.get();
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public Page<Apk> queryByPage(Long gameId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findByGame_Id(gameId, pageable);
    }

    public Apk update(@PathVariable Long id, Apk apk) {
        if (repository.existsById(id)) {
            return repository.save(apk);
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public void delete(Long id) {
        Optional<Apk> apk = repository.findById(id);
        if(apk.isPresent()) {
            repository.deleteById(id);
            File apkFile = new File(apkPath, apk.get().getFileName());
            apkFile.delete();
        }
    }
}