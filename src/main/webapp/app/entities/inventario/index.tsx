import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Inventario from './inventario';
import InventarioDetail from './inventario-detail';
import InventarioUpdate from './inventario-update';
import InventarioDeleteDialog from './inventario-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InventarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InventarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InventarioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Inventario} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InventarioDeleteDialog} />
  </>
);

export default Routes;
