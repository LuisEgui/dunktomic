package es.ucm.luisegui.dunktomic.errors;

import es.ucm.luisegui.dunktomic.rest.dtos.Error;
import es.ucm.luisegui.dunktomic.rest.dtos.FieldErrors;
import java.util.Collections;
import java.util.List;

public interface RestError {

    String getCode();
    String getDescription();

    default Error error(String code, String description, List<FieldErrors> fields) {
        Error error = new Error();
        error.setCode(code != null ? code : getCode());
        error.setDescription(description != null ? description : getDescription());
        error.setFields(fields);
        return error;
    }

    default Error error(String code, String description) {
        return error(code, description, null);
    }

    default Error error(List<FieldErrors> fields) {
        return error(null, null, fields);
    }

    default Error error() {
        return error(null, null, Collections.emptyList());
    }

    default Error error(String description) {
        return error(null, description);
    }

    RestError NOT_FOUND = new RestError() {
        @Override
        public String getCode() {
            return "NOT_FOUND";
        }

        @Override
        public String getDescription() {
            return "Resource not found";
        }
    };

    RestError BAD_REQUEST = new RestError() {
        @Override
        public String getCode() {
            return "BAD_REQUEST";
        }

        @Override
        public String getDescription() {
            return "Bad request";
        }
    };

    RestError CONFLICT = new RestError() {
        @Override
        public String getCode() {
            return "CONFLICT";
        }

        @Override
        public String getDescription() {
            return "Conflict with the data provided";
        }
    };

    RestError INTERNAL_SERVER_ERROR = new RestError() {
        @Override
        public String getCode() {
            return "INTERNAL_SERVER_ERROR";
        }

        @Override
        public String getDescription() {
            return "Internal server error";
        }
    };
}
