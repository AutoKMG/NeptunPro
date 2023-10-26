package hu.unideb.inf.NeptunPro.repository;

import hu.unideb.inf.NeptunPro.model.Bag;
import hu.unideb.inf.NeptunPro.model.BagKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BagRepository extends JpaRepository<Bag, Long> {
    Bag findByBkey(final BagKey key);
}
