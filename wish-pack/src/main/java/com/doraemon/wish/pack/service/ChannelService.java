package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.Channel;
import com.doraemon.wish.pack.dao.repository.ChannelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class ChannelService {

    private final ChannelRepository repository;

    public ChannelService(ChannelRepository repository) {
        this.repository = repository;
    }

    public Channel create(Channel channel) {
        return repository.save(channel);
    }

    public Channel query(Long id) {
        Optional<Channel> channel = repository.findById(id);
        if (channel.isPresent()) {
            return channel.get();
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public Page<Channel> queryByPage(Long gameId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findByGame_Id(gameId, pageable);
    }

    public Channel update(@PathVariable Long id, Channel channel) {
        if (repository.existsById(id)) {
            return repository.save(channel);
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}