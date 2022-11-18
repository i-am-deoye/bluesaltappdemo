package com.bluesaltapp.common.service;

import com.bluesaltapp.common.GlobalMessage;
import com.bluesaltapp.common.Message;
import com.bluesaltapp.common.ReflectionUtils;
import com.bluesaltapp.common.exception.DataValidationException;
import com.bluesaltapp.common.exception.NotFoundException;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseValidationService {

    protected <E> void validateSaveEntity(E entity) throws DataValidationException {
        if (Objects.isNull(entity)) {
            GlobalMessage message = new GlobalMessage("empty entity", "", Message.Severity.ERROR );
            throw new DataValidationException(message);
        }
    }

    protected <E>void validateSaveEntity(E entity, String ...properties) throws DataValidationException {
        if (entity == null) {
            GlobalMessage message = new GlobalMessage("empty entity", "", Message.Severity.ERROR );
            throw new DataValidationException(message);
        } else {

            for (String property : properties) {
                Optional<Object> optional = ReflectionUtils.getPropertyValue(property, entity.getClass());
                if (optional.isPresent()) {
                    GlobalMessage message = new GlobalMessage("entity could not be saved : entity id found to contain value", "", Message.Severity.ERROR );
                    throw new DataValidationException(message);
                }
            }
        }
    }

    protected <E>void validateUpdateEntity(E entity, String ...properties) throws DataValidationException {
        if (entity == null) {
            GlobalMessage message = new GlobalMessage("empty entity", "", Message.Severity.ERROR );
            throw new DataValidationException(message);
        } else {

            for (String property : properties) {
                Optional<Object> optional = ReflectionUtils.getPropertyValue(property, entity.getClass());
                if (optional.isPresent()) {
                    GlobalMessage message = new GlobalMessage("entity could not be updated : entity id found to be nil", "", Message.Severity.ERROR );
                    throw new DataValidationException(message);
                }
            }
        }
    }

    protected <E>void validateOptionalEntity(Optional<E> optionalEntity) throws NotFoundException {
        if (!optionalEntity.isPresent()) {
            GlobalMessage message = new GlobalMessage("Data not found", "", Message.Severity.ERROR );
            throw new NotFoundException( message );
        }
    }

    protected <T> void validateCollectionEntities(List<T> entities) throws NotFoundException {
        if (entities.isEmpty()) {
            GlobalMessage message = new GlobalMessage("Empty Collection", "", Message.Severity.ERROR );
            throw new NotFoundException( message );
        }
    }

    protected void validateIsNullEntity(Optional optionalEntity) throws DataValidationException {
        if (optionalEntity.isPresent()) {
            GlobalMessage message = new GlobalMessage("Key was created for this user", "", Message.Severity.ERROR );
            throw new DataValidationException( message );
        }
    }

    protected <E extends Serializable>void validateJointEntity(E jointEntity) throws DataValidationException {
        if (jointEntity == null) {
            GlobalMessage message = new GlobalMessage("empty entity", "", Message.Severity.ERROR );
            throw new DataValidationException(message);
        } else {
            Optional<Object> optional = ReflectionUtils.getPropertyValue("id", jointEntity.getClass());
            if (optional.isPresent()) {
                GlobalMessage message = new GlobalMessage("joint entity could not be validated : entity id found to be nil", "", Message.Severity.ERROR );
                throw new DataValidationException(message);
            }
        }
    }
}
