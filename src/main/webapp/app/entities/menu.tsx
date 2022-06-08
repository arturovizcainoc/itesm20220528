import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/inventario">
        <Translate contentKey="global.menu.entities.inventario" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/recursos-humanos">
        <Translate contentKey="global.menu.entities.recursosHumanos" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
