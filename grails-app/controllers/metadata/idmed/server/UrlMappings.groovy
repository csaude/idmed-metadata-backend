package metadata.csaude.server

class UrlMappings {

    static mappings = {
        get "/api/$controller(.$format)?"(action:"index")
        get "/api/$controller/$id(.$format)?"(action:"show")
        post "/api/$controller(.$format)?"(action:"save")
        put "/api/$controller/$id(.$format)?"(action:"update")
        patch "/api/$controller/$id(.$format)?"(action:"patch")

        get "/api/drug/drugFromMetadata/$offset(.$format)?"(controller:'drug', action:'getDrugsFromMetadataServer')
        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
