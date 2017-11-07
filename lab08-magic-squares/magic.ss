; Lab 8 - Magic squares
; Completed by Kristian Snyder
; 6 November 2017

(define call/cc call-with-current-continuation)

(define all-done
    (let ((halt (call/cc (lambda (k) k))))
      (lambda () (halt 'no-solution))))

(define-syntax amb
    (syntax-rules ()
      ((amb x ...)
       (let ((prev-level all-done))
         (call/cc 
           (lambda (sk)
             (call/cc 
               (lambda (fk) 
                 (set! all-done (lambda () (fk 'fail)))
             (sk x)))
             ...
             (prev-level)))))))

(define assert (lambda (p) (if (not p) (amb) 'success)))

(define (member? x lst)
(if (null? lst) #f
    (if (equal? x (car lst)) #t
         (member? x (cdr lst)))))

(define (distinct? lst)
  (if (null? lst)
    #t
    (and (not (member? (car lst) (cdr lst))) (distinct? (cdr lst)))))

(define (magic)
    (let* ((s11 (amb 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16)) ; Initial values
          (s13 (amb 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16))
          (s14 (amb 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16))
          (s12 (- 34 (+ s14 s13 s11))) ; Row 1 satisfaction
          (s23 (amb 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16))
          (s32 (amb 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16))
          (s41 (- 34 (+ s14 s23 s32))) ; Diagonal satisfaction
          (s44 (- 34 (+ s11 s14 s41))) ; Outer box satisfaction
          (s22 (amb 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16))
          (s33 (- 34 (+ s32 s22 s23))) ; Inner box satisfaction
          (s42 (- 34 (+ s32 s22 s12))) ; Column 2
          (s43 (- 34 (+ s13 s23 s33))) ; Column 3
          (s34 (amb 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16))
          (s24 (- 34 (+ s14 s34 s44))) ; Column 4
          (s31 (- 34 (+ s32 s33 s34))) ; Row 3
          (s21 (- 34 (+ s22 s23 s24))) ; Row 2
         )
      (let ((lst (list s11 s12 s13 s14 s21 s22 s23 s24 s31 s32 s33 s34 s41 s42 s43 s44)))
            ; Make sure none of the calculated cells are out of bounds
            (assert (and (<= s12 16) (<= 1 s12)))
            (assert (and (<= s41 16) (<= 1 s41)))
            (assert (and (<= s44 16) (<= 1 s44)))
            (assert (and (<= s33 16) (<= 1 s33)))
            (assert (and (<= s42 16) (<= 1 s42)))
            (assert (and (<= s43 16) (<= 1 s43)))
            (assert (and (<= s24 16) (<= 1 s24)))
            (assert (and (<= s31 16) (<= 1 s31)))
            (assert (and (<= s21 16) (<= 1 s21)))
            (assert (distinct? lst)) ; Check whether the list has any duplicate numbers
            (list 
              (list s11 s12 s13 s14)
              (list s21 s22 s23 s24)
              (list s31 s32 s33 s34)
              (list s41 s42 s43 s44)))))

(magic)