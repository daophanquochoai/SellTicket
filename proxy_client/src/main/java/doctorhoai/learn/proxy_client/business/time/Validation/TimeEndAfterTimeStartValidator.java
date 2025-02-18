package doctorhoai.learn.proxy_client.business.time.Validation;

import doctorhoai.learn.proxy_client.business.time.model.request.FilmShowRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class TimeEndAfterTimeStartValidator implements ConstraintValidator<ValidTimeRange, FilmShowRequest> {
    @Override
    public boolean isValid(FilmShowRequest filmShowRequest, ConstraintValidatorContext constraintValidatorContext) {
        if( filmShowRequest.getTimeStart() == null || filmShowRequest.getTimeEnd() == null ){
            return true;
        }
        return filmShowRequest.getTimeEnd().after(filmShowRequest.getTimeStart());
    }
}
