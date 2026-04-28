export interface Producto {
  id: number | null;
  nombreBase: string;
  variante: string | null;
  medidaValor: number | null;
  medidaUnidad: string | null;
  sku: string | null;
  precioCosto: number | null;
  precioVenta: number | null;
  imagenUrl: string | null;
  estado: boolean;
  nombreMarca: string | null;
  nombreSubCategoria: string | null;
}
