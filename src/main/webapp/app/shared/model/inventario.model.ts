import dayjs from 'dayjs';
import { inventarioTipo } from 'app/shared/model/enumerations/inventario-tipo.model';

export interface IInventario {
  id?: string;
  producto?: string | null;
  fechaIngreso?: string | null;
  fechaSalida?: string | null;
  tipo?: inventarioTipo | null;
}

export const defaultValue: Readonly<IInventario> = {};
