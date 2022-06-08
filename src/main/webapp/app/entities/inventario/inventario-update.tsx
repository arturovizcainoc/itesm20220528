import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInventario } from 'app/shared/model/inventario.model';
import { inventarioTipo } from 'app/shared/model/enumerations/inventario-tipo.model';
import { getEntity, updateEntity, createEntity, reset } from './inventario.reducer';

export const InventarioUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const inventarioEntity = useAppSelector(state => state.inventario.entity);
  const loading = useAppSelector(state => state.inventario.loading);
  const updating = useAppSelector(state => state.inventario.updating);
  const updateSuccess = useAppSelector(state => state.inventario.updateSuccess);
  const inventarioTipoValues = Object.keys(inventarioTipo);
  const handleClose = () => {
    props.history.push('/inventario');
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
      ...inventarioEntity,
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
          tipo: 'CAJA',
          ...inventarioEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="itesm20220528App.inventario.home.createOrEditLabel" data-cy="InventarioCreateUpdateHeading">
            <Translate contentKey="itesm20220528App.inventario.home.createOrEditLabel">Create or edit a Inventario</Translate>
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
                  id="inventario-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('itesm20220528App.inventario.producto')}
                id="inventario-producto"
                name="producto"
                data-cy="producto"
                type="text"
              />
              <ValidatedField
                label={translate('itesm20220528App.inventario.fechaIngreso')}
                id="inventario-fechaIngreso"
                name="fechaIngreso"
                data-cy="fechaIngreso"
                type="date"
              />
              <ValidatedField
                label={translate('itesm20220528App.inventario.fechaSalida')}
                id="inventario-fechaSalida"
                name="fechaSalida"
                data-cy="fechaSalida"
                type="date"
              />
              <ValidatedField
                label={translate('itesm20220528App.inventario.tipo')}
                id="inventario-tipo"
                name="tipo"
                data-cy="tipo"
                type="select"
              >
                {inventarioTipoValues.map(inventarioTipo => (
                  <option value={inventarioTipo} key={inventarioTipo}>
                    {translate('itesm20220528App.inventarioTipo.' + inventarioTipo)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inventario" replace color="info">
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

export default InventarioUpdate;
