import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RecursosHumanos from './recursos-humanos';
import RecursosHumanosDetail from './recursos-humanos-detail';
import RecursosHumanosUpdate from './recursos-humanos-update';
import RecursosHumanosDeleteDialog from './recursos-humanos-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RecursosHumanosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RecursosHumanosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RecursosHumanosDetail} />
      <ErrorBoundaryRoute path={match.url} component={RecursosHumanos} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RecursosHumanosDeleteDialog} />
  </>
);

export default Routes;
