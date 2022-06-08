import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRecursosHumanos } from 'app/shared/model/recursos-humanos.model';
import { tipoPuesto } from 'app/shared/model/enumerations/tipo-puesto.model';
import { getEntity, updateEntity, createEntity, reset } from './recursos-humanos.reducer';

export const RecursosHumanosUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const recursosHumanosEntity = useAppSelector(state => state.recursosHumanos.entity);
  const loading = useAppSelector(state => state.recursosHumanos.loading);
  const updating = useAppSelector(state => state.recursosHumanos.updating);
  const updateSuccess = useAppSelector(state => state.recursosHumanos.updateSuccess);
  const tipoPuestoValues = Object.keys(tipoPuesto);
  const handleClose = () => {
    props.history.push('/recursos-humanos');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...recursosHumanosEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          puesto: 'DESARROLLADOR',
          ...recursosHumanosEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="itesm20220528App.recursosHumanos.home.createOrEditLabel" data-cy="RecursosHumanosCreateUpdateHeading">
            <Translate contentKey="itesm20220528App.recursosHumanos.home.createOrEditLabel">Create or edit a RecursosHumanos</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="recursos-humanos-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('itesm20220528App.recursosHumanos.nombre')}
                id="recursos-humanos-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('itesm20220528App.recursosHumanos.apellidoPaterno')}
                id="recursos-humanos-apellidoPaterno"
                name="apellidoPaterno"
                data-cy="apellidoPaterno"
                type="text"
              />
              <ValidatedField
                label={translate('itesm20220528App.recursosHumanos.apellidoMaterno')}
                id="recursos-humanos-apellidoMaterno"
                name="apellidoMaterno"
                data-cy="apellidoMaterno"
                type="text"
              />
              <ValidatedField
                label={translate('itesm20220528App.recursosHumanos.fechaNacimiento')}
                id="recursos-humanos-fechaNacimiento"
                name="fechaNacimiento"
                data-cy="fechaNacimiento"
                type="date"
              />
              <ValidatedField
                label={translate('itesm20220528App.recursosHumanos.puesto')}
                id="recursos-humanos-puesto"
                name="puesto"
                data-cy="puesto"
                type="select"
              >
                {tipoPuestoValues.map(tipoPuesto => (
                  <option value={tipoPuesto} key={tipoPuesto}>
                    {translate('itesm20220528App.tipoPuesto.' + tipoPuesto)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/recursos-humanos" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RecursosHumanosUpdate;
