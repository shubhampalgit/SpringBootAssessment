package com.cg.web;

import com.cg.entity.Track;
import com.cg.repo.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/music")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    // POST /music/tracks - Add a new track
    @PostMapping("/tracks")
    public ResponseEntity<String> addTrack(@RequestBody Track track) {
        trackRepository.save(track);
        return new ResponseEntity<>("Track Added ", HttpStatus.CREATED);
    }

    // GET /music/tracks - Get all tracks
    @GetMapping("/tracks")
    public ResponseEntity<List<Track>> getTracks() {
        List<Track> tracks = trackRepository.findAll();
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    // GET /music/tracks/search?title=... - Search tracks by title
    @GetMapping("/tracks/search")
    public ResponseEntity<List<Track>> getTracksByTitle(@RequestParam String title) {
        List<Track> tracks = trackRepository.findByTitleContainingIgnoreCase(title);
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    // GET /music/tracks/{id} - Get track by ID
    @GetMapping("/tracks/{id}")
    public ResponseEntity<Object> getTrack(@PathVariable Long id) {
        Optional<Track> track = trackRepository.findById(id);
        if (track.isPresent()) {
            return new ResponseEntity<>(track.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Track not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}