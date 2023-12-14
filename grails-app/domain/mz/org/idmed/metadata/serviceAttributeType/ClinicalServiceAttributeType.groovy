package mz.org.idmed.metadata.serviceAttributeType

class ClinicalServiceAttributeType {
    String id
    String code
    String description

    static mapping = {
        id generator: "assigned"
        id column: 'id', index: 'Pk_ClinicalServiceAttributeType_Idx'
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
