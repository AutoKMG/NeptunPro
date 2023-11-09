package hu.unideb.inf.NeptunPro.domain.repo;

import hu.unideb.inf.NeptunPro.domain.model._sys.Bag;
import hu.unideb.inf.NeptunPro.domain.model._sys.BagKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BagRepository extends JpaRepository<Bag, Long> {
    Bag findByBkey(final BagKey key);
}
