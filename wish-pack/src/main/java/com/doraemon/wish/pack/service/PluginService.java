package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.Plugin;
import com.doraemon.wish.pack.dao.repository.PluginRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class PluginService {

    private final PluginRepository repository;

    public PluginService(PluginRepository repository) {
        this.repository = repository;
    }

    public Plugin create(Plugin plugin) {
        return repository.save(plugin);
    }

    public Plugin query(Long id) {
        Optional<Plugin> plugin = repository.findById(id);
        if (plugin.isPresent()) {
            return plugin.get();
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public Page<Plugin> queryByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAll(pageable);
    }

    public Plugin update(@PathVariable Long id, Plugin plugin) {
        if (repository.existsById(id)) {
            return repository.save(plugin);
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}