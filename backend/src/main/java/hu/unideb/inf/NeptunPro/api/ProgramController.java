package hu.unideb.inf.NeptunPro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.NeptunPro.domain.model.Program;
import hu.unideb.inf.NeptunPro.domain.repo.ProgramRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static hu.unideb.inf.NeptunPro.util.Utils.logApi;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/program")
public class ProgramController {

    private final ObjectMapper mapper;

    private final ProgramRepository programRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllPrograms(final Principal principal) {
        logApi(HttpMethod.GET, principal, "getAllPrograms");
        var lst = programRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok(lst);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByName(
            @PathVariable("name") final String name,
            final Principal principal
    ) {
        logApi(HttpMethod.GET, principal, String.format("searchByName [%s]", name));
        var arrayNode = mapper.createArrayNode();

        var programs = programRepository.findTop10ByNameContains(name);
        for (var program : programs) {
            var node = arrayNode.addObject();
            node.put("id", program.getId());
            node.put("name", program.getName());
        }

        return ResponseEntity.ok(arrayNode);
    }

    @PostMapping("")
    public ResponseEntity<?> createProgram(
            @Valid @RequestBody final Program received,
            final Principal principal
    ) {

        logApi(HttpMethod.POST, principal, "createProgram");
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
            @PathVariable final Long id,
            final Principal principal
    )
    {
        logApi(HttpMethod.PUT, principal, String.format("updateProgram [%d]", id));
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
    public ResponseEntity<?> deleteProgram(
            @PathVariable final Long id,
            final Principal principal
    ) {
        logApi(HttpMethod.DELETE, principal, String.format("deleteProgram [%d]", id));
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
