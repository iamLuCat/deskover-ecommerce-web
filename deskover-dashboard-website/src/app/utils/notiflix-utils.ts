import Swal from 'sweetalert2';
import {Confirm, Notify} from "notiflix";

export class NotiflixUtils {
  constructor() { }

  static showConfirm(title: string, message: string, okCallback: Function) {
    Confirm.show(
      title,
      message,
      'Có',
      'Không',
      () => {
        okCallback();
      },
      () => {
        // do nothing
      },
      {
        // button background color
        okButtonBackground: '#1c519d',
        titleColor: '#1c519d',
      }
    )
  }

  static successNotify(title: string) {
    Notify.success(title);
  }

  static failureNotify(title: string) {
    Notify.failure(title);
  }

  static warningNotify(title: string) {
    Notify.warning(title);
  }
}
