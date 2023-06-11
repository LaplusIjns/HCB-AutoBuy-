package immargin.hardware.hcb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import immargin.hardware.hcb.model.Blacklist;

public interface BlacklistRepository extends JpaRepository<Blacklist,String>,JpaSpecificationExecutor<Blacklist> {

}
