package hu.unideb.inf.NeptunPro.util;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BagRepository extends JpaRepository<Bag, Long> {
    Bag findByBkey(final BagKey key);
}
