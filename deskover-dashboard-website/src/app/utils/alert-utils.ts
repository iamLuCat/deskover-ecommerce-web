import Swal from 'sweetalert2';

export class AlertUtils {
  private static toast: any = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.addEventListener('mouseenter', Swal.stopTimer)
      toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
  })

  constructor() { }

  static error(title: string, message: string) {
    return Swal.fire({
      icon: 'error',
      title: title,
      text: message,
      showCancelButton: true,
      cancelButtonColor: '#d33',
      cancelButtonText: 'Không',
      confirmButtonColor: '#3085d6',
      confirmButtonText: 'Có',
    });
  }

  static info(title: string, message: string) {
    return Swal.fire({
      icon: 'info',
      title: title,
      text: message,
      showCancelButton: true,
      cancelButtonColor: '#d33',
      cancelButtonText: 'Không',
      confirmButtonColor: '#3085d6',
      confirmButtonText: 'Có',
    });
  }

  static warning(title: string, message: string) {
    return Swal.fire({
      icon: 'warning',
      title: title,
      text: message,
      showCancelButton: true,
      cancelButtonColor: '#d33',
      cancelButtonText: 'Không',
      confirmButtonColor: '#3085d6',
      confirmButtonText: 'Có',
    });
  }

  static toastSuccess(title: string) {
    this.toast.fire({
      icon: 'success',
      title: title,
    });
  }

  static toastError(title: string) {
    this.toast.fire({
      icon: 'error',
      title: title,
    });
  }

  static toastWarning(title: string) {
    this.toast.fire({
      icon: 'warning',
      title: title,
    });
  }
}
