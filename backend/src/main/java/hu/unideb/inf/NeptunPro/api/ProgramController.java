package hu.unideb.inf.NeptunPro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.NeptunPro.domain.model.Program;
import hu.unideb.inf.NeptunPro.domain.repo.ProgramRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/program")
public class ProgramController {

    private final ObjectMapper mapper;

    private final ProgramRepository programRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllPrograms() {
        var lst = programRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok(lst);
    }

    @PostMapping("")
    public ResponseEntity<?> createCourse(@Valid @RequestBody final Program received) {

        // TODO: add auth admin check
        var json = mapper.createObjectNode();

        if(programRepository.existsByName(received.getName())) {
            json.put("error",String.format("Program with name [%s] already exists", received.getName()));
        }

        try{
            var saved = programRepository.save(received);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            json.put("error", e.getMessage());
        }

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProgram(
            @Valid @RequestBody final Program received,
            @PathVariable final Long id)
    {
        // TODO: add auth admin check
        var json = mapper.createObjectNode();

        var programFromDb = programRepository.findById(id).orElse(null);

        if(programFromDb != null) {
            try {
                programFromDb.setName(received.getName());

                var updated = programRepository.saveAndFlush(programFromDb);
                return new ResponseEntity<>(updated, HttpStatus.OK);
            } catch (Exception e) {
                json.put("error", e.getMessage());
            }
        }

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProgram(@PathVariable final Long id) {
        // TODO: add auth admin check
        var json = mapper.createObjectNode();
        try {
            programRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            json.put("error", e.getMessage());
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
    }
}
