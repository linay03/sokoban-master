(define (problem Sokoban_p01)

    (:domain Sokoban)

    (:objects
        box1 - box
        guard - guard

        pos_1_1 pos_1_2 pos_1_3
        pos_2_1 pos_2_2 pos_2_3
        pos_3_1 pos_3_2 pos_3_3
        - position

        U D L R - direction
    )

    (:init

        ;; Position du guard
        (at-position guard pos_1_1)

        ;; Position de la boîte
        (at-box box1 pos_1_2)

        ;; Destination
        (destination pos_1_3)

        ;; Cases libres
        (clear pos_1_1)
        (clear pos_1_3)
        (clear pos_2_1)
        (clear pos_2_2)
        (clear pos_2_3)
        (clear pos_3_1)
        (clear pos_3_2)
        (clear pos_3_3)

        ;; Positions adjacentes
        (adjacent pos_1_1 pos_1_2)
        (adjacent pos_1_2 pos_1_1)

        (adjacent pos_1_2 pos_1_3)
        (adjacent pos_1_3 pos_1_2)

        (adjacent pos_2_1 pos_2_2)
        (adjacent pos_2_2 pos_2_1)

        (adjacent pos_2_2 pos_2_3)
        (adjacent pos_2_3 pos_2_2)

        (adjacent pos_3_1 pos_3_2)
        (adjacent pos_3_2 pos_3_1)

        (adjacent pos_3_2 pos_3_3)
        (adjacent pos_3_3 pos_3_2)

        (adjacent pos_1_1 pos_2_1)
        (adjacent pos_2_1 pos_1_1)

        (adjacent pos_1_2 pos_2_2)
        (adjacent pos_2_2 pos_1_2)

        (adjacent pos_1_3 pos_2_3)
        (adjacent pos_2_3 pos_1_3)

        (adjacent pos_2_1 pos_3_1)
        (adjacent pos_3_1 pos_2_1)

        (adjacent pos_2_2 pos_3_2)
        (adjacent pos_3_2 pos_2_2)

        (adjacent pos_2_3 pos_3_3)
        (adjacent pos_3_3 pos_2_3)


        ;; Déplacements vers la droite
        (next-move pos_1_1 pos_1_2 R)
        (next-move pos_1_2 pos_1_3 R)

        (next-move pos_2_1 pos_2_2 R)
        (next-move pos_2_2 pos_2_3 R)

        (next-move pos_3_1 pos_3_2 R)
        (next-move pos_3_2 pos_3_3 R)


        ;; Déplacements vers la gauche
        (next-move pos_1_2 pos_1_1 L)
        (next-move pos_1_3 pos_1_2 L)

        (next-move pos_2_2 pos_2_1 L)
        (next-move pos_2_3 pos_2_2 L)

        (next-move pos_3_2 pos_3_1 L)
        (next-move pos_3_3 pos_3_2 L)


        ;; Déplacements vers le bas
        (next-move pos_1_1 pos_2_1 D)
        (next-move pos_1_2 pos_2_2 D)
        (next-move pos_1_3 pos_2_3 D)

        (next-move pos_2_1 pos_3_1 D)
        (next-move pos_2_2 pos_3_2 D)
        (next-move pos_2_3 pos_3_3 D)


        ;; Déplacements vers le haut
        (next-move pos_2_1 pos_1_1 U)
        (next-move pos_2_2 pos_1_2 U)
        (next-move pos_2_3 pos_1_3 U)

        (next-move pos_3_1 pos_2_1 U)
        (next-move pos_3_2 pos_2_2 U)
        (next-move pos_3_3 pos_2_3 U)
    )

    (:goal
        (at-box box1 pos_1_3)
    )
)