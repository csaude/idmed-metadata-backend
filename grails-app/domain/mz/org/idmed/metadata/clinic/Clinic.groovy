package mz.org.idmed.metadata.clinic

import mz.org.idmed.metadata.district.District
import mz.org.idmed.metadata.facilityType.FacilityType
import mz.org.idmed.metadata.province.Province

class Clinic {

    String id
    String code
    String notes
    String telephone
    String clinicName
    Province province
    District district
    FacilityType facilityType
    Boolean nationalClinic
    boolean mainClinic
    boolean active
    String uuid
    String matchFC
    static belongsTo = []
    // static hasMany = [sectors: ClinicSector]

    static mapping = {
        id generator: "assigned"
        id column: 'id', index: 'Pk_Clinic_Idx'
    }
    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false
        notes nullable: true, blank: true
        telephone nullable: true, matches: /\d+/, maxSize: 12, minSize: 9
        clinicName nullable: false, unique: ['province','district']
        nationalClinic nullable: true
        uuid unique: true
        matchFC nullable: true
    }
}
