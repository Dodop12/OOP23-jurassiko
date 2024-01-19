package it.unibo.jurassiko.reader.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unibo.jurassiko.model.objective.api.Objective;
import it.unibo.jurassiko.reader.api.JSONFileReader;

public class ObjectiveReader implements JSONFileReader<Objective> {

    final Logger logger = LoggerFactory.getLogger(ObjectiveReader.class);
    private final ObjectMapper mapper;

    public ObjectiveReader() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public Set<Objective> readFileData(String path) {
        Set<Objective> objectives = new HashSet<>();

        try (final InputStream in = Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(path))) {
            objectives = mapper.readValue(in,
                    new TypeReference<Set<Objective>>() {
                    });
            writeDescriptions(objectives);
        } catch (final IOException e) {
            this.logger.error("Failed to read objectives file", e);
        }

        return objectives;
    }

    private void writeDescriptions(Set<Objective> objectives) {
        for (var objective : objectives) {
            objective.writeDescription();
        }
    }

}