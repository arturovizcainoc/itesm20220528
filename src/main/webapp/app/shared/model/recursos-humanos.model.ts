import dayjs from 'dayjs';
import { tipoPuesto } from 'app/shared/model/enumerations/tipo-puesto.model';

export interface IRecursosHumanos {
  id?: string;
  nombre?: string | null;
  apellidoPaterno?: string | null;
  apellidoMaterno?: string | null;
  fechaNacimiento?: string | null;
  puesto?: tipoPuesto | null;
}

export const defaultValue: Readonly<IRecursosHumanos> = {};
