package mz.org.idmed.metadata.form

class Form {

    String id
    String code
    String description

    static mapping = {
        id generator: "assigned"
        id column: 'id', index: 'Pk_Form_Idx'
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false, blank: false
    }

}
