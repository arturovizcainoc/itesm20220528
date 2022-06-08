import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inventario.reducer';

export const InventarioDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const inventarioEntity = useAppSelector(state => state.inventario.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inventarioDetailsHeading">
          <Translate contentKey="itesm20220528App.inventario.detail.title">Inventario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{inventarioEntity.id}</dd>
          <dt>
            <span id="producto">
              <Translate contentKey="itesm20220528App.inventario.producto">Producto</Translate>
            </span>
          </dt>
          <dd>{inventarioEntity.producto}</dd>
          <dt>
            <span id="fechaIngreso">
              <Translate contentKey="itesm20220528App.inventario.fechaIngreso">Fecha Ingreso</Translate>
            </span>
          </dt>
          <dd>
            {inventarioEntity.fechaIngreso ? (
              <TextFormat value={inventarioEntity.fechaIngreso} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaSalida">
              <Translate contentKey="itesm20220528App.inventario.fechaSalida">Fecha Salida</Translate>
            </span>
          </dt>
          <dd>
            {inventarioEntity.fechaSalida ? (
              <TextFormat value={inventarioEntity.fechaSalida} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="itesm20220528App.inventario.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{inventarioEntity.tipo}</dd>
        </dl>
        <Button tag={Link} to="/inventario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inventario/${inventarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InventarioDetail;
