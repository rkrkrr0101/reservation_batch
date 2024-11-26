package rkrk.batch.infra.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rkrk.batch.infra.persistence.entity.WareHouseJpaEntity

@Repository
interface WareHouseJpaRepository : JpaRepository<WareHouseJpaEntity, Long>
