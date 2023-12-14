package mz.org.idmed.metadata.serviceAttribute

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ClinicalServiceAttributeController {

    ClinicalServiceAttributeService clinicalServiceAttributeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond clinicalServiceAttributeService.list(params), model:[clinicalServiceAttributeCount: clinicalServiceAttributeService.count()]
    }

    def show(Long id) {
        respond clinicalServiceAttributeService.get(id)
    }

    @Transactional
    def save(ClinicalServiceAttribute clinicalServiceAttribute) {
        if (clinicalServiceAttribute == null) {
            render status: NOT_FOUND
            return
        }
        if (clinicalServiceAttribute.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond clinicalServiceAttribute.errors
            return
        }

        try {
            clinicalServiceAttributeService.save(clinicalServiceAttribute)
        } catch (ValidationException e) {
            respond clinicalServiceAttribute.errors
            return
        }

        respond clinicalServiceAttribute, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ClinicalServiceAttribute clinicalServiceAttribute) {
        if (clinicalServiceAttribute == null) {
            render status: NOT_FOUND
            return
        }
        if (clinicalServiceAttribute.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond clinicalServiceAttribute.errors
            return
        }

        try {
            clinicalServiceAttributeService.save(clinicalServiceAttribute)
        } catch (ValidationException e) {
            respond clinicalServiceAttribute.errors
            return
        }

        respond clinicalServiceAttribute, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || clinicalServiceAttributeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
