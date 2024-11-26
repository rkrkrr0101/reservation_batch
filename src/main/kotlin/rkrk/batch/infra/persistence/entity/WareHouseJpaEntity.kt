package rkrk.batch.infra.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "warehouse")
class WareHouseJpaEntity(
    @Column(unique = true, nullable = false)
    var name: String,
    var capacity: Long,
    @Column(nullable = false)
    var minutePrice: Long,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wareHouse", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var reservationJpaEntities: MutableList<ReservationJpaEntity>,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Suppress("unused")
    var id: Long = 0,
) : BaseEntity()
