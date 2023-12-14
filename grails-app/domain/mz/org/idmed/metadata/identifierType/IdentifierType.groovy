package mz.org.idmed.metadata.identifierType

class IdentifierType {

    String id
    String code
    String description
    String pattern

    static mapping = {
        id generator: "assigned"
        id column: 'id', index: 'Pk_IdentifierType_Idx'
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
        pattern nullable: true
    }
}
