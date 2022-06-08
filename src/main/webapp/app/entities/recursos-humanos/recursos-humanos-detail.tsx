import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './recursos-humanos.reducer';

export const RecursosHumanosDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const recursosHumanosEntity = useAppSelector(state => state.recursosHumanos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recursosHumanosDetailsHeading">
          <Translate contentKey="itesm20220528App.recursosHumanos.detail.title">RecursosHumanos</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{recursosHumanosEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="itesm20220528App.recursosHumanos.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{recursosHumanosEntity.nombre}</dd>
          <dt>
            <span id="apellidoPaterno">
              <Translate contentKey="itesm20220528App.recursosHumanos.apellidoPaterno">Apellido Paterno</Translate>
            </span>
          </dt>
          <dd>{recursosHumanosEntity.apellidoPaterno}</dd>
          <dt>
            <span id="apellidoMaterno">
              <Translate contentKey="itesm20220528App.recursosHumanos.apellidoMaterno">Apellido Materno</Translate>
            </span>
          </dt>
          <dd>{recursosHumanosEntity.apellidoMaterno}</dd>
          <dt>
            <span id="fechaNacimiento">
              <Translate contentKey="itesm20220528App.recursosHumanos.fechaNacimiento">Fecha Nacimiento</Translate>
            </span>
          </dt>
          <dd>
            {recursosHumanosEntity.fechaNacimiento ? (
              <TextFormat value={recursosHumanosEntity.fechaNacimiento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="puesto">
              <Translate contentKey="itesm20220528App.recursosHumanos.puesto">Puesto</Translate>
            </span>
          </dt>
          <dd>{recursosHumanosEntity.puesto}</dd>
        </dl>
        <Button tag={Link} to="/recursos-humanos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recursos-humanos/${recursosHumanosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RecursosHumanosDetail;
