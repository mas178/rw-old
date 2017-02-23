package works.rational.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import works.rational.domain.Status;

public interface StatusRepository extends JpaRepository<Status, String>, JpaSpecificationExecutor<Status> {

  class StatusSpecifications {
    public static Specification<Status> domainIs(final String domain) {
      return StringUtils.isEmpty(domain) ? null : (root, query, cb) -> cb.equal(root.get("domain"), domain);
    }
  }
}
