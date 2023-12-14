package mz.org.idmed.metadata.server

class Server {

    String id
    String code
    String urlPath
    String port
    String destination
    String username
    String password

    static mapping = {
        id generator: "assigned"

    }

    static constraints = {
        urlPath(nullable: false, blank: false)
        code(nullable: false, maxSize: 50, blank: false,unique: ['destination'])
        port(nullable: true)
        destination(nullable: false,blank: false)
        username(nullable: false,blank: false)
        password(nullable: false,blank: false)
    }


}
