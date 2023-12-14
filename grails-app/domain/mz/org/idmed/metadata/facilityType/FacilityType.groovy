package mz.org.idmed.metadata.facilityType

class FacilityType {

    String id
    String code
    String description

    static mapping = {
        id generator: "assigned"
        id column: 'id', index: 'Pk_FacilityType_Idx'
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }
    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }

}
