package mz.org.idmed.metadata.form

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class FormController {

    FormService formService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond formService.list(params), model:[formCount: formService.count()]
    }

    def show(Long id) {
        respond formService.get(id)
    }

    @Transactional
    def save(Form form) {
        if (form == null) {
            render status: NOT_FOUND
            return
        }
        if (form.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond form.errors
            return
        }

        try {
            formService.save(form)
        } catch (ValidationException e) {
            respond form.errors
            return
        }

        respond form, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Form form) {
        if (form == null) {
            render status: NOT_FOUND
            return
        }
        if (form.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond form.errors
            return
        }

        try {
            formService.save(form)
        } catch (ValidationException e) {
            respond form.errors
            return
        }

        respond form, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || formService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
