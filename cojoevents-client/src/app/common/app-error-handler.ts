import { ErrorHandler } from '@angular/core';

export class AppErrorHandler implements ErrorHandler {
   public handleError(message) {
     console.log(message);

  }
}
